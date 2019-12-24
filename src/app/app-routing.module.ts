import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './paginas/login/login.component';
import { DashboardComponent } from './paginas/dashboard/dashboard.component';
import { AuthGuard } from './guards/auth.guard';
import { NoAuthGuard } from './guards/no-auth.guard';
import { CadastroUsuarioComponent } from './paginas/cadastro-usuario/cadastro-usuario.component';
import { OperacoesComponent } from './paginas/operacoes/operacoes.component';


const routes: Routes = [
  {
    path: '',
    component: LoginComponent,
    canActivate: [NoAuthGuard]
  },
  {
    path: 'cadastre-se',
    component: CadastroUsuarioComponent,
    canActivate: [NoAuthGuard]
  },
  {
    path: 'operacao',
    component: OperacoesComponent,
    canActivate: [NoAuthGuard]
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
