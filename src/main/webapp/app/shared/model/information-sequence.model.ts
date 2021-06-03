import { IItem } from 'app/shared/model/item.model';

export interface IInformationSequence {
  id?: number;
  infSeq?: number | null;
  item?: IItem | null;
}

export const defaultValue: Readonly<IInformationSequence> = {};
