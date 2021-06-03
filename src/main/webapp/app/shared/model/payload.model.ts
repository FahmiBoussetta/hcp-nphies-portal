import { IAttachment } from 'app/shared/model/attachment.model';
import { IReferenceIdentifier } from 'app/shared/model/reference-identifier.model';
import { ICommunication } from 'app/shared/model/communication.model';
import { ICommunicationRequest } from 'app/shared/model/communication-request.model';

export interface IPayload {
  id?: number;
  contentString?: string | null;
  contentAttachment?: IAttachment | null;
  contentReference?: IReferenceIdentifier | null;
  communication?: ICommunication | null;
  communicationRequest?: ICommunicationRequest | null;
}

export const defaultValue: Readonly<IPayload> = {};
