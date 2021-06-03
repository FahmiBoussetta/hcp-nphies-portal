import { ICommunication } from 'app/shared/model/communication.model';
import { CommunicationMediumEnum } from 'app/shared/model/enumerations/communication-medium-enum.model';

export interface IListCommunicationMediumEnum {
  id?: number;
  cm?: CommunicationMediumEnum | null;
  communication?: ICommunication | null;
}

export const defaultValue: Readonly<IListCommunicationMediumEnum> = {};
