import { IItem } from 'app/shared/model/item.model';

export interface IDiagnosisSequence {
  id?: number;
  diagSeq?: number | null;
  item?: IItem | null;
}

export const defaultValue: Readonly<IDiagnosisSequence> = {};
