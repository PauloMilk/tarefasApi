import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Tarefa } from '../interfaces/tarefa';

@Injectable({
  providedIn: 'root'
})
export class TarefaService {

  constructor(private http: HttpClient) { }

  getListaTarefas(): Observable<any> {
    const url = `${environment.urlAPI}/tarefas`;
    return this.http.get(url);
  }

  getListaTarefasPorStatus(size: number, page: number, status: string): Observable<any> {
    const url = `${environment.urlAPI}/tarefas/status/${status}?page=${page}&size=${size}`;
    return this.http.get(url);
  }

  postTarefa(tarefa: Tarefa): Observable<any> {
    const url = `${environment.urlAPI}/tarefas`;
    return this.http.post(url, tarefa);
  }

  putTarefa(tarefa: Tarefa): Observable<any> {
    const url = `${environment.urlAPI}/tarefas/${tarefa.id}`;
    return this.http.put(url, tarefa);
  }

  deleteTarefa(id: number) {
    const url = `${environment.urlAPI}/tarefas/${id}`;
    return this.http.delete(url);
  }
}
