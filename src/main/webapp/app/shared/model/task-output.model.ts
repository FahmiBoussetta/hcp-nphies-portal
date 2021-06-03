import { IReferenceIdentifier } from 'app/shared/model/reference-identifier.model';
import { ITaskResponse } from 'app/shared/model/task-response.model';

export interface ITaskOutput {
  id?: number;
  status?: string | null;
  errorOutput?: string | null;
  response?: IReferenceIdentifier | null;
  taskResponse?: ITaskResponse | null;
}

export const defaultValue: Readonly<ITaskOutput> = {};
