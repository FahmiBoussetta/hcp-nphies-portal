import { IOrganization } from 'app/shared/model/organization.model';
import { ITaskInput } from 'app/shared/model/task-input.model';
import { TaskCodeEnum } from 'app/shared/model/enumerations/task-code-enum.model';
import { TaskReasonCodeEnum } from 'app/shared/model/enumerations/task-reason-code-enum.model';

export interface ITask {
  id?: number;
  guid?: string | null;
  isQueued?: boolean | null;
  parsed?: string | null;
  identifier?: string | null;
  code?: TaskCodeEnum | null;
  description?: string | null;
  focus?: string | null;
  reasonCode?: TaskReasonCodeEnum | null;
  requester?: IOrganization | null;
  owner?: IOrganization | null;
  inputs?: ITaskInput[] | null;
}

export const defaultValue: Readonly<ITask> = {
  isQueued: false,
};
