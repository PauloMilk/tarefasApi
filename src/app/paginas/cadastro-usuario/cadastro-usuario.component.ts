import { Component, OnInit } from '@angular/core';
import { Usuario } from 'src/app/interfaces/usuario';
import { UsuarioService } from 'src/app/services/usuario.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cadastro-usuario',
  templateUrl: './cadastro-usuario.component.html',
  styleUrls: ['./cadastro-usuario.component.css']
})
export class CadastroUsuarioComponent implements OnInit {
  public usuario: Usuario = <Usuario>{};
  public error: string[] = [];
  public sucesso: string;
  constructor(private router: Router, private usuarioService: UsuarioService) { }

  ngOnInit() {
  }

  onSubmit() {
    this.error = [];
    this.sucesso = null;
    if (this.usuario.senha === this.usuario.senhaConfirm) {
      this.usuarioService.cadastrarUsuario(this.usuario).subscribe(
        (user) => {
         this.usuario = <Usuario>{};
         this.sucesso = "Cadastro efetuado com sucesso, em alguns instantes verifique seu email para confirmacao de sua conta.";
        },
        (erro: any) => {
          console.log(erro);
          if (erro.status === 400) {
            erro.error.errors.forEach(element => {
              this.error.push(element);
            });
          }
        }
      );
    } else {
      this.error.push("Senha diferentes!");
    }

  }

}
