import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './paginas/login/login.component';
//

import {
  MatButtonModule, MatCardModule, MatDialogModule, MatInputModule, MatTableModule,
  MatToolbarModule, MatMenuModule, MatIconModule, MatProgressSpinnerModule
} from '@angular/material';
import {MatGridListModule} from '@angular/material/grid-list';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { FormsModule } from '@angular/forms';
import { DashboardComponent } from './paginas/dashboard/dashboard.component';
import {MatTabsModule} from '@angular/material/tabs';
import { ListaTarefasComponent } from './paginas/lista-tarefas/lista-tarefas.component';
import {MatDividerModule} from '@angular/material/divider';
import {MatPaginatorModule, MatPaginatorIntl} from '@angular/material/paginator';
import { TokenApiService } from './interceptores/token-api.service';
import { InvalidTokenApiService } from './interceptores/invalid-token-api.service';
import { FormTarefaComponent } from './shared/form-tarefa/form-tarefa.component';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatSelectModule} from '@angular/material/select';
import {MatCheckboxModule} from '@angular/material/checkbox';
import { ConfirmDialogComponent } from './shared/confirm-dialog/confirm-dialog.component';
import { CadastroUsuarioComponent } from './paginas/cadastro-usuario/cadastro-usuario.component';
import { ConfirmarContaComponent } from './shared/confirmar-conta/confirmar-conta.component';
import { OperacoesComponent } from './paginas/operacoes/operacoes.component';
import { EsqueceuSenhaComponent } from './shared/esqueceu-senha/esqueceu-senha.component';
import { RecuperarSenhaComponent } from './shared/recuperar-senha/recuperar-senha.component';
import { getPortuguesePaginatorIntl } from './config/ptbr-paginator-intl';

import {PickListModule} from 'primeng/picklist';
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NavbarComponent,
    DashboardComponent,
    ListaTarefasComponent,
    FormTarefaComponent,
    ConfirmDialogComponent,
    CadastroUsuarioComponent,
    ConfirmarContaComponent,
    OperacoesComponent,
    EsqueceuSenhaComponent,
    RecuperarSenhaComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    //
    MatToolbarModule,
    MatButtonModule,
    MatCardModule,
    MatInputModule,
    MatDialogModule,
    MatTableModule,
    MatMenuModule,
    MatIconModule,
    MatProgressSpinnerModule,
    BrowserAnimationsModule,
    MatGridListModule,
    MatTabsModule,
    MatDividerModule,
    MatPaginatorModule,
    MatFormFieldModule,
    MatSelectModule,
    MatCheckboxModule,
    PickListModule
  ],
  entryComponents: [
    FormTarefaComponent,
    ConfirmDialogComponent
  ],
  providers: [  {
    provide: HTTP_INTERCEPTORS,
    useClass: TokenApiService,
    multi: true
  },
  {
    provide: HTTP_INTERCEPTORS,
    useClass: InvalidTokenApiService,
    multi: true
  },
  {provide: MatPaginatorIntl, useValue: getPortuguesePaginatorIntl() }
],
  bootstrap: [AppComponent]
})
export class AppModule { }
