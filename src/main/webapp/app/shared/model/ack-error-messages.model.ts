import { IAcknowledgement } from 'app/shared/model/acknowledgement.model';

export interface IAckErrorMessages {
  id?: number;
  message?: string | null;
  acknowledgement?: IAcknowledgement | null;
}

export const defaultValue: Readonly<IAckErrorMessages> = {};
