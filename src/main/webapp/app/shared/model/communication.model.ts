import { IPatient } from 'app/shared/model/patient.model';
import { IOrganization } from 'app/shared/model/organization.model';
import { IClaim } from 'app/shared/model/claim.model';
import { ICommunicationRequest } from 'app/shared/model/communication-request.model';
import { IListCommunicationMediumEnum } from 'app/shared/model/list-communication-medium-enum.model';
import { IListCommunicationReasonEnum } from 'app/shared/model/list-communication-reason-enum.model';
import { IPayload } from 'app/shared/model/payload.model';
import { INote } from 'app/shared/model/note.model';
import { IComErrorMessages } from 'app/shared/model/com-error-messages.model';
import { CommunicationPriorityEnum } from 'app/shared/model/enumerations/communication-priority-enum.model';

export interface ICommunication {
  id?: number;
  guid?: string | null;
  isQueued?: boolean | null;
  parsed?: string | null;
  identifier?: string | null;
  priority?: CommunicationPriorityEnum | null;
  subject?: IPatient | null;
  sender?: IOrganization | null;
  recipient?: IOrganization | null;
  about?: IClaim | null;
  basedOns?: ICommunicationRequest[] | null;
  mediums?: IListCommunicationMediumEnum[] | null;
  reasonCodes?: IListCommunicationReasonEnum[] | null;
  payloads?: IPayload[] | null;
  notes?: INote[] | null;
  errors?: IComErrorMessages[] | null;
}

export const defaultValue: Readonly<ICommunication> = {
  isQueued: false,
};
