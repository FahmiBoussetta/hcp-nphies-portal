import { IOperationOutcome } from 'app/shared/model/operation-outcome.model';

export interface IOpeOutErrorMessages {
  id?: number;
  message?: string | null;
  operationOutcome?: IOperationOutcome | null;
}

export const defaultValue: Readonly<IOpeOutErrorMessages> = {};
