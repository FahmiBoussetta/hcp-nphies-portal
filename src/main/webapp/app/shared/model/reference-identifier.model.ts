import { IItem } from 'app/shared/model/item.model';
import { IDetailItem } from 'app/shared/model/detail-item.model';
import { ISubDetailItem } from 'app/shared/model/sub-detail-item.model';

export interface IReferenceIdentifier {
  id?: number;
  ref?: string | null;
  idValue?: string | null;
  identifier?: string | null;
  display?: string | null;
  item?: IItem | null;
  detailItem?: IDetailItem | null;
  subDetailItem?: ISubDetailItem | null;
}

export const defaultValue: Readonly<IReferenceIdentifier> = {};
