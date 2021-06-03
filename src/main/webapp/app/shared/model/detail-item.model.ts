import { IReferenceIdentifier } from 'app/shared/model/reference-identifier.model';
import { ISubDetailItem } from 'app/shared/model/sub-detail-item.model';
import { IItem } from 'app/shared/model/item.model';

export interface IDetailItem {
  id?: number;
  sequence?: number;
  tax?: number | null;
  transportationSRCA?: string | null;
  imaging?: string | null;
  laboratory?: string | null;
  medicalDevice?: string | null;
  oralHealthIP?: string | null;
  oralHealthOP?: string | null;
  procedure?: string | null;
  services?: string | null;
  medicationCode?: string | null;
  quantity?: number;
  unitPrice?: number;
  udis?: IReferenceIdentifier[] | null;
  subDetails?: ISubDetailItem[] | null;
  item?: IItem | null;
}

export const defaultValue: Readonly<IDetailItem> = {};
