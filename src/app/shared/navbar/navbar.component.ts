import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { FormTarefaComponent } from '../form-tarefa/form-tarefa.component';
import { MatDialog } from '@angular/material';
import { Tarefa } from 'src/app/interfaces/tarefa';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  @Input() autenticado: boolean = false;
  @Output() atualizar: EventEmitter<any> = new EventEmitter();
  private tarefa: Tarefa = {
    descricao: '',
    status: 'TODO'
  };
  constructor(private authService: AuthService, public dialog: MatDialog) { }

  ngOnInit() {
  }

  sair() {
    this.authService.deslogar();
  }

  openTask() {

    const dialogRef = this.dialog.open(FormTarefaComponent,  { width: '460px', data: {
      tarefa: this.tarefa,
      title: 'Nova Tarefa'
    } });
    dialogRef.afterClosed().subscribe(result => {
      this.tarefa = {
        descricao: '',
        status: 'TODO'
      };
    });
  }
}
