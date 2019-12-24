import { Component, OnInit } from '@angular/core';
import { Usuario } from 'src/app/interfaces/usuario';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public usuario: Usuario = <Usuario>{};
  public error: string;
  constructor(private authService: AuthService) { }

  ngOnInit() {
  }

  onSubmit() {
    this.error = null;
    this.authService.logar(this.usuario).subscribe(
      (token) => {
      },
      (erro: any) => {
        this.error = erro.errors;
      }
    );
  }

}
