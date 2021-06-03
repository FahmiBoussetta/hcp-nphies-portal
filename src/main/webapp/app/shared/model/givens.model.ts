import { IHumanName } from 'app/shared/model/human-name.model';

export interface IGivens {
  id?: number;
  given?: string;
  prefix?: string | null;
  suffix?: string | null;
  textName?: string | null;
  human?: IHumanName | null;
}

export const defaultValue: Readonly<IGivens> = {};
