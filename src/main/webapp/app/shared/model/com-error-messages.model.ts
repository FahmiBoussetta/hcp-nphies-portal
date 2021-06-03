import { ICommunication } from 'app/shared/model/communication.model';

export interface IComErrorMessages {
  id?: number;
  message?: string | null;
  communication?: ICommunication | null;
}

export const defaultValue: Readonly<IComErrorMessages> = {};
