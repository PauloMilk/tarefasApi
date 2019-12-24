import { Component, OnInit, Inject, EventEmitter } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { TarefaService } from 'src/app/services/tarefa.service';
import { Tarefa } from 'src/app/interfaces/tarefa';
import { EventEmitterService } from 'src/app/services/eventEmitter.service';

export interface DialogData {
  tarefa: Tarefa;
  title: string;
}

@Component({
  selector: 'app-form-tarefa',
  templateUrl: './form-tarefa.component.html',
  styleUrls: ['./form-tarefa.component.css']
})
export class FormTarefaComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<FormTarefaComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private tarefaService: TarefaService
  ) { }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit() {

  }

  onSubmit() {
    if (this.data.tarefa.id != null) {
      this.tarefaService.putTarefa(this.data.tarefa).subscribe(
        (data) =>  this.enviarEvento(),
        (erro) => console.log(erro)
      );
    } else {
      this.tarefaService.postTarefa(this.data.tarefa).subscribe(
        (data) => this.enviarEvento(),
        (erro) => console.log(erro)
      );
    }
  }

  enviarEvento() {
    this.dialogRef.close();
    EventEmitterService.get('atualizarTabela').emit(this.data.tarefa.status);
  }

}
