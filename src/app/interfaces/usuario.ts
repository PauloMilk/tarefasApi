export interface Usuario {
  id?: number;
  nome?: string;
  email: string;
  senha: string;
  senhaConfirm?: string;
  codigo?: string;
}
