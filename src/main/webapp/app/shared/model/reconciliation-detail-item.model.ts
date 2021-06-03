import dayjs from 'dayjs';
import { IClaim } from 'app/shared/model/claim.model';
import { IOrganization } from 'app/shared/model/organization.model';
import { IClaimResponse } from 'app/shared/model/claim-response.model';
import { IPaymentReconciliation } from 'app/shared/model/payment-reconciliation.model';

export interface IReconciliationDetailItem {
  id?: number;
  identifier?: string | null;
  predecessor?: string | null;
  type?: string | null;
  date?: string | null;
  amount?: number | null;
  request?: IClaim | null;
  submitter?: IOrganization | null;
  response?: IClaimResponse | null;
  payee?: IOrganization | null;
  paymentReconciliation?: IPaymentReconciliation | null;
}

export const defaultValue: Readonly<IReconciliationDetailItem> = {};
