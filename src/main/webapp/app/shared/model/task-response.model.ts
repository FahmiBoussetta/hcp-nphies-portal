import { ITaskOutput } from 'app/shared/model/task-output.model';

export interface ITaskResponse {
  id?: number;
  value?: string | null;
  system?: string | null;
  parsed?: string | null;
  status?: string | null;
  outputs?: ITaskOutput[] | null;
}

export const defaultValue: Readonly<ITaskResponse> = {};
