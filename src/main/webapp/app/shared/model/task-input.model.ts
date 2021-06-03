import dayjs from 'dayjs';
import { IReferenceIdentifier } from 'app/shared/model/reference-identifier.model';
import { ITask } from 'app/shared/model/task.model';
import { ResourceTypeEnum } from 'app/shared/model/enumerations/resource-type-enum.model';
import { EventCodingEnum } from 'app/shared/model/enumerations/event-coding-enum.model';

export interface ITaskInput {
  id?: number;
  inputInclude?: ResourceTypeEnum | null;
  inputExclude?: ResourceTypeEnum | null;
  inputIncludeMessage?: EventCodingEnum | null;
  inputExcludeMessage?: EventCodingEnum | null;
  inputCount?: number | null;
  inputStart?: string | null;
  inputEnd?: string | null;
  inputLineItem?: number | null;
  inputOrigResponse?: IReferenceIdentifier | null;
  task?: ITask | null;
}

export const defaultValue: Readonly<ITaskInput> = {};
