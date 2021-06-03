import { IReferenceIdentifier } from 'app/shared/model/reference-identifier.model';
import { IClaim } from 'app/shared/model/claim.model';
import { ClaimRelationshipEnum } from 'app/shared/model/enumerations/claim-relationship-enum.model';

export interface IRelated {
  id?: number;
  relationShip?: ClaimRelationshipEnum | null;
  claimReference?: IReferenceIdentifier | null;
  claim?: IClaim | null;
}

export const defaultValue: Readonly<IRelated> = {};
