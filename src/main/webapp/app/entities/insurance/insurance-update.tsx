import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICoverage } from 'app/shared/model/coverage.model';
import { getEntities as getCoverages } from 'app/entities/coverage/coverage.reducer';
import { IClaimResponse } from 'app/shared/model/claim-response.model';
import { getEntities as getClaimResponses } from 'app/entities/claim-response/claim-response.reducer';
import { IClaim } from 'app/shared/model/claim.model';
import { getEntities as getClaims } from 'app/entities/claim/claim.reducer';
import { getEntity, updateEntity, createEntity, reset } from './insurance.reducer';
import { IInsurance } from 'app/shared/model/insurance.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IInsuranceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const InsuranceUpdate = (props: IInsuranceUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { insuranceEntity, coverages, claimResponses, claims, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/insurance');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCoverages();
    props.getClaimResponses();
    props.getClaims();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...insuranceEntity,
        ...values,
        coverage: coverages.find(it => it.id.toString() === values.coverageId.toString()),
        claimResponse: claimResponses.find(it => it.id.toString() === values.claimResponseId.toString()),
        claim: claims.find(it => it.id.toString() === values.claimId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.insurance.home.createOrEditLabel" data-cy="InsuranceCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.insurance.home.createOrEditLabel">Create or edit a Insurance</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : insuranceEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="insurance-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="insurance-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="sequenceLabel" for="insurance-sequence">
                  <Translate contentKey="hcpNphiesPortalApp.insurance.sequence">Sequence</Translate>
                </Label>
                <AvField
                  id="insurance-sequence"
                  data-cy="sequence"
                  type="string"
                  className="form-control"
                  name="sequence"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="focalLabel">
                  <AvInput id="insurance-focal" data-cy="focal" type="checkbox" className="form-check-input" name="focal" />
                  <Translate contentKey="hcpNphiesPortalApp.insurance.focal">Focal</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="preAuthRefLabel" for="insurance-preAuthRef">
                  <Translate contentKey="hcpNphiesPortalApp.insurance.preAuthRef">Pre Auth Ref</Translate>
                </Label>
                <AvField id="insurance-preAuthRef" data-cy="preAuthRef" type="text" name="preAuthRef" />
              </AvGroup>
              <AvGroup>
                <Label for="insurance-coverage">
                  <Translate contentKey="hcpNphiesPortalApp.insurance.coverage">Coverage</Translate>
                </Label>
                <AvInput id="insurance-coverage" data-cy="coverage" type="select" className="form-control" name="coverageId">
                  <option value="" key="0" />
                  {coverages
                    ? coverages.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="insurance-claimResponse">
                  <Translate contentKey="hcpNphiesPortalApp.insurance.claimResponse">Claim Response</Translate>
                </Label>
                <AvInput id="insurance-claimResponse" data-cy="claimResponse" type="select" className="form-control" name="claimResponseId">
                  <option value="" key="0" />
                  {claimResponses
                    ? claimResponses.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="insurance-claim">
                  <Translate contentKey="hcpNphiesPortalApp.insurance.claim">Claim</Translate>
                </Label>
                <AvInput id="insurance-claim" data-cy="claim" type="select" className="form-control" name="claimId">
                  <option value="" key="0" />
                  {claims
                    ? claims.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/insurance" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  coverages: storeState.coverage.entities,
  claimResponses: storeState.claimResponse.entities,
  claims: storeState.claim.entities,
  insuranceEntity: storeState.insurance.entity,
  loading: storeState.insurance.loading,
  updating: storeState.insurance.updating,
  updateSuccess: storeState.insurance.updateSuccess,
});

const mapDispatchToProps = {
  getCoverages,
  getClaimResponses,
  getClaims,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(InsuranceUpdate);
