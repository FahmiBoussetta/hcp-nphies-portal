import { IOpeOutErrorMessages } from 'app/shared/model/ope-out-error-messages.model';

export interface IOperationOutcome {
  id?: number;
  value?: string | null;
  system?: string | null;
  parsed?: string | null;
  errors?: IOpeOutErrorMessages[] | null;
}

export const defaultValue: Readonly<IOperationOutcome> = {};
