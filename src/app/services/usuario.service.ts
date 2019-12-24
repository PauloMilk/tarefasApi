import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Usuario } from '../interfaces/usuario';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(private http: HttpClient) { }

  cadastrarUsuario(usuario: Usuario): Observable<any> {
    const url = `${environment.urlAPI}/usuarios`;
    return this.http.post(url, usuario);
  }

  ativarConta(codigo: string): Observable<any> {
    const url = `${environment.urlAPI}/usuarios/confirmacao?codigo=${codigo}`;
    return this.http.get(url);
  }

  solicitarRecuperacao(email: string): Observable<any> {
    const url = `${environment.urlAPI}/usuarios/recuperacao?email=${email}`;
    return this.http.post(url, null);
  }

  redefinirSenha(usuario: Usuario): Observable<any> {
    const url = `${environment.urlAPI}/usuarios/recuperacao`;
    return this.http.put(url, usuario);
  }


}
