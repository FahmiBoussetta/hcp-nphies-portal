import dayjs from 'dayjs';
import { IPatient } from 'app/shared/model/patient.model';
import { IClaim } from 'app/shared/model/claim.model';
import { IOrganization } from 'app/shared/model/organization.model';
import { IPayload } from 'app/shared/model/payload.model';
import { INote } from 'app/shared/model/note.model';
import { ICommunication } from 'app/shared/model/communication.model';

export interface ICommunicationRequest {
  id?: number;
  value?: string | null;
  system?: string | null;
  parsed?: string | null;
  limitDate?: string | null;
  subject?: IPatient | null;
  about?: IClaim | null;
  sender?: IOrganization | null;
  payloads?: IPayload[] | null;
  notes?: INote[] | null;
  communication?: ICommunication | null;
}

export const defaultValue: Readonly<ICommunicationRequest> = {};
