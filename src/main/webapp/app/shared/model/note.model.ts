import dayjs from 'dayjs';
import { ICommunication } from 'app/shared/model/communication.model';
import { ICommunicationRequest } from 'app/shared/model/communication-request.model';

export interface INote {
  id?: number;
  text?: string | null;
  author?: string | null;
  time?: string | null;
  communication?: ICommunication | null;
  communicationRequest?: ICommunicationRequest | null;
}

export const defaultValue: Readonly<INote> = {};
