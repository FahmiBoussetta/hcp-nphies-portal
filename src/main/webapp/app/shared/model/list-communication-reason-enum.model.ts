import { ICommunication } from 'app/shared/model/communication.model';
import { CommunicationReasonEnum } from 'app/shared/model/enumerations/communication-reason-enum.model';

export interface IListCommunicationReasonEnum {
  id?: number;
  cr?: CommunicationReasonEnum | null;
  communication?: ICommunication | null;
}

export const defaultValue: Readonly<IListCommunicationReasonEnum> = {};
