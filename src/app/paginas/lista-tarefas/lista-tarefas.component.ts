import { Component, OnInit, ViewChild, AfterViewInit, Input, OnDestroy } from '@angular/core';
import { MatPaginator, MatTableDataSource, MatSort, MatDialog } from '@angular/material';
import { TarefaService } from 'src/app/services/tarefa.service';
import { Observable, merge, of as observableOf } from 'rxjs';
import { catchError, map, startWith, switchMap } from 'rxjs/operators';
import { Tarefa } from 'src/app/interfaces/tarefa';
import { FormTarefaComponent } from 'src/app/shared/form-tarefa/form-tarefa.component';
import { EventEmitterService } from 'src/app/services/eventEmitter.service';
import { ConfirmDialogComponent } from 'src/app/shared/confirm-dialog/confirm-dialog.component';
import { HostListener } from "@angular/core";

@Component({
  selector: 'app-lista-tarefas',
  templateUrl: './lista-tarefas.component.html',
  styleUrls: ['./lista-tarefas.component.css']
})
export class ListaTarefasComponent implements AfterViewInit {
  @Input() status = 'DONE';
  displayedColumns: string[] = ['descricao', 'status', 'dataCriacao', 'dataAtualizacao', 'actions'];
  data: Tarefa[] = [];
  exampleDatabase: ExampleHttpDatabase | null;
  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;
  public tarefaSelected: Tarefa;
  screenWidth: number;

  constructor(private tarefaService: TarefaService, public dialog: MatDialog) {
    EventEmitterService.get('atualizarTabela').subscribe(data => {
      this.ngAfterViewInit();
    });
    this.screenWidth = (window.screen.width);
    if (this.screenWidth <= 968) {
      this.displayedColumns = ['descricao', 'status', 'actions'];
    }
  }

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;


  openTask(id: number, descricao: string, status: string) {
    const tarefa: Tarefa = {
      id,
      descricao,
      status
    };
    const dialogRef = this.dialog.open(FormTarefaComponent, {
      width: '460px', data: {
        tarefa,
        title: 'Editando Tarefa'
      }
    });
  }

  ngAfterViewInit() {
    this.exampleDatabase = new ExampleHttpDatabase(this.tarefaService);
    merge(this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          return this.exampleDatabase!.getRepoIssues(
            this.paginator.pageSize, this.paginator.pageIndex, this.status.replace(' ', ''));
        }),
        map(data => {
          // Flip flag to show that loading has finished.
          this.isLoadingResults = false;
          this.isRateLimitReached = false;
          this.resultsLength = data.numberOfElements;

          return data.content;
        }),
        catchError(() => {
          this.isLoadingResults = false;
          // Catch if the GitHub API has reached its rate limit. Return empty data.
          this.isRateLimitReached = true;
          return observableOf([]);
        })
      ).subscribe(data => { this.data = data; console.log(this.status) });
    console.log(this.data)
  }


  deleteTarefa(id: number) {
    this.tarefaService.deleteTarefa(id).subscribe(
      (data) => this.ngAfterViewInit(),
      (erro) => console.log(erro)
    );
  }

  openConfirmDelete(id) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: {
        message: 'Deseja mesmo remover essa tarefa?',
        buttonText: {
          ok: 'Sim, desejo.',
          cancel: 'Nao'
        }
      }
    });
    dialogRef.afterClosed().subscribe((confirmed: boolean) => {
      if (confirmed) {
        this.deleteTarefa(id);
      }
    });
  }
}

export class ExampleHttpDatabase {
  constructor(private tarefaService: TarefaService) { }

  getRepoIssues(size: number, page: number, status: string): Observable<any> {
    return this.tarefaService.getListaTarefasPorStatus(size, page, status);
  }
}
