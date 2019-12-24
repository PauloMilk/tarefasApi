import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {map} from 'rxjs/operators';
@Component({
  selector: 'app-operacoes',
  templateUrl: './operacoes.component.html',
  styleUrls: ['./operacoes.component.css']
})
export class OperacoesComponent implements OnInit {
  public operacao = 'confirmacao';
  private codigo;
  constructor(private route: ActivatedRoute) {
    this.route.queryParams.subscribe(params => {
       this.operacao = params['op'];
       this.codigo =  params['cod'];
    });
   }

  ngOnInit() {
  }

}
