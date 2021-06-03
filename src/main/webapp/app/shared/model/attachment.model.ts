import { LanguageEnum } from 'app/shared/model/enumerations/language-enum.model';

export interface IAttachment {
  id?: number;
  contentType?: string | null;
  title?: string | null;
  language?: LanguageEnum | null;
  isData?: boolean | null;
  dataFileContentType?: string | null;
  dataFile?: string | null;
  url?: string | null;
  attachmentSize?: number | null;
  hashContentType?: string | null;
  hash?: string | null;
}

export const defaultValue: Readonly<IAttachment> = {
  isData: false,
};
