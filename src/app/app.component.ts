import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from './services/auth.service';
import { TokenService } from './services/token.service';
import { TarefaService } from './services/tarefa.service';
import { ListaTarefasComponent } from './paginas/lista-tarefas/lista-tarefas.component';
import { Location } from '@angular/common';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  constructor(private router: Router, private location: Location, public authService: AuthService, private tokenService: TokenService, private ser: TarefaService) {
  }
  public autenticado: boolean = false;
  ngOnInit() {
    if (this.tokenService.token) {
      this.authService.criarSessao(this.tokenService.token);
    }

    this.authService.autenticado$.subscribe(autenticado => {
      this.autenticado = autenticado;
      if (autenticado) {
        this.router.navigate(['/dashboard']);
      } else if (this.location.path() === '/dashboard') {
        this.router.navigate(['/']);
      }
    });
  }

}
