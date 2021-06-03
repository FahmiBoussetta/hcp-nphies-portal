import { IAckErrorMessages } from 'app/shared/model/ack-error-messages.model';

export interface IAcknowledgement {
  id?: number;
  value?: string | null;
  system?: string | null;
  parsed?: string | null;
  errors?: IAckErrorMessages[] | null;
}

export const defaultValue: Readonly<IAcknowledgement> = {};
