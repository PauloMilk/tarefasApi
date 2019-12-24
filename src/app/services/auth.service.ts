import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Usuario } from '../interfaces/usuario';
import { Observable, BehaviorSubject } from 'rxjs';
import { environment } from 'src/environments/environment';
import { map, finalize } from 'rxjs/operators';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private _autenticado: BehaviorSubject<boolean>;
  public readonly autenticado$: Observable<boolean>;
  constructor(private http: HttpClient,  private tokenService: TokenService) {
    this._autenticado = new BehaviorSubject(false);
    this.autenticado$ = this._autenticado.asObservable();
  }


  logar(usuario: Usuario): Observable<boolean> {
    const url = `${environment.urlAPI}/auth`;
    return this.http.post(url, usuario).pipe(
      map((resp: any) => {
        if (!this.criarSessao(resp.token)) {
          throw new Error();
        }
        return true;
      })
    );
  }

  criarSessao(token: string): boolean {
    try {
      //this.usuarioService.setUsuario(usuario);
      this.tokenService.token = token;
      this._autenticado.next(true);
      return true;
    } catch (err) {
      return false;
    }
  }

  deslogar() {
    this.resetarSessao();
  }

  resetarSessao() {
    this.tokenService.resetarToken();
    if (this._autenticado.value) {
      this._autenticado.next(false);
    }
  }

}
