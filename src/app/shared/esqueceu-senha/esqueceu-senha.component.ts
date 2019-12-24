import { Component, OnInit } from '@angular/core';
import { Usuario } from 'src/app/interfaces/usuario';
import { Router } from '@angular/router';
import { UsuarioService } from 'src/app/services/usuario.service';

@Component({
  selector: 'app-esqueceu-senha',
  templateUrl: './esqueceu-senha.component.html',
  styleUrls: ['./esqueceu-senha.component.css']
})
export class EsqueceuSenhaComponent implements OnInit {

  public usuario: Usuario = <Usuario>{};
  public error: string[] = [];
  public sucesso: string;
  constructor(private router: Router, private usuarioService: UsuarioService) { }

  ngOnInit() {
  }

  onSubmit() {
    this.error = [];
    this.sucesso = null;
    if (this.usuario.email != null) {
      this.usuarioService.solicitarRecuperacao(this.usuario.email).subscribe(
        (user) => {
          this.usuario = <Usuario>{};
          this.sucesso = "Solicitacao efetuada com sucesso, em alguns instantes verifique seu email para realizar a redefinicao.";
        },
        (erro: any) => {
          console.log(erro);

          erro.error.forEach(element => {
            this.error.push(element);
          });

        }
      );
    } else {
      this.error.push("Email nao pode ser nulo!");
    }

  }
}
