import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UsuarioService } from 'src/app/services/usuario.service';

@Component({
  selector: 'app-confirmar-conta',
  templateUrl: './confirmar-conta.component.html',
  styleUrls: ['./confirmar-conta.component.css']
})
export class ConfirmarContaComponent implements OnInit {
  ativado: boolean = false;
  private codigo;
  public sucesso:string;
  public erro: string;

  constructor(private route: ActivatedRoute, private usuarioService: UsuarioService) {
    this.route.queryParams.subscribe(params => {
       this.codigo =  params['cod'];
       console.log(this.codigo);
    });
   }
  ngOnInit() {
  }

  onSubmit() {
    this.erro = null;
    this.sucesso = null;
    this.ativado = false;
    if (this.codigo != null) {
      this.usuarioService.ativarConta(this.codigo).subscribe(
        (data) => {
          this.sucesso = "Conta ativada com sucesso."
          this.ativado = true;
        },
        (erro) => this.erro = erro.error
      );
    } else {
      this.erro = "Codigo inexistente";
    }
  }

}
