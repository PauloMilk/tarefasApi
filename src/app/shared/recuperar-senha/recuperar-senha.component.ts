import { Component, OnInit } from '@angular/core';
import { Usuario } from 'src/app/interfaces/usuario';
import { Router, ActivatedRoute } from '@angular/router';
import { UsuarioService } from 'src/app/services/usuario.service';

@Component({
  selector: 'app-recuperar-senha',
  templateUrl: './recuperar-senha.component.html',
  styleUrls: ['./recuperar-senha.component.css']
})
export class RecuperarSenhaComponent implements OnInit {
  public ativado: boolean = false;
  public usuario: Usuario = <Usuario>{};
  public error: string[] = [];
  public sucesso: string;
  constructor(private route: ActivatedRoute, private usuarioService: UsuarioService) {
    this.route.queryParams.subscribe(params => {
      this.usuario.codigo = params['cod'];
    });
  }

  ngOnInit() {
  }

  onSubmit() {
    this.error = [];
    this.sucesso = null;
    if (this.usuario.senha === this.usuario.senhaConfirm) {
      this.usuarioService.redefinirSenha(this.usuario).subscribe(
        (user) => {
          this.usuario = <Usuario>{};
          this.ativado = true;
        },
        (erro: any) => {
          console.log(erro);
          erro.error.forEach(element => {
            this.error.push(element);
          });

        }
      );
    } else {
      this.error.push("Senha diferentes!");
    }

  }

}
