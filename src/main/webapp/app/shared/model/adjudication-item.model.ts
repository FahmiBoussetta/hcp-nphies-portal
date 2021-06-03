import { IAdjudicationNotes } from 'app/shared/model/adjudication-notes.model';
import { IAdjudication } from 'app/shared/model/adjudication.model';
import { IAdjudicationDetailItem } from 'app/shared/model/adjudication-detail-item.model';
import { IClaimResponse } from 'app/shared/model/claim-response.model';

export interface IAdjudicationItem {
  id?: number;
  outcome?: string | null;
  sequence?: number;
  notes?: IAdjudicationNotes[] | null;
  adjudications?: IAdjudication[] | null;
  details?: IAdjudicationDetailItem[] | null;
  claimResponse?: IClaimResponse | null;
}

export const defaultValue: Readonly<IAdjudicationItem> = {};
