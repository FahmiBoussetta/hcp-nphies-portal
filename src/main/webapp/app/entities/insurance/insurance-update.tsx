import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

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
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const InsuranceUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const coverages = useAppSelector(state => state.coverage.entities);
  const claimResponses = useAppSelector(state => state.claimResponse.entities);
  const claims = useAppSelector(state => state.claim.entities);
  const insuranceEntity = useAppSelector(state => state.insurance.entity);
  const loading = useAppSelector(state => state.insurance.loading);
  const updating = useAppSelector(state => state.insurance.updating);
  const updateSuccess = useAppSelector(state => state.insurance.updateSuccess);

  const handleClose = () => {
    props.history.push('/insurance');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCoverages({}));
    dispatch(getClaimResponses({}));
    dispatch(getClaims({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...insuranceEntity,
      ...values,
      coverage: coverages.find(it => it.id.toString() === values.coverageId.toString()),
      claimResponse: claimResponses.find(it => it.id.toString() === values.claimResponseId.toString()),
      claim: claims.find(it => it.id.toString() === values.claimId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...insuranceEntity,
          coverageId: insuranceEntity?.coverage?.id,
          claimResponseId: insuranceEntity?.claimResponse?.id,
          claimId: insuranceEntity?.claim?.id,
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
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="insurance-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.insurance.sequence')}
                id="insurance-sequence"
                name="sequence"
                data-cy="sequence"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.insurance.focal')}
                id="insurance-focal"
                name="focal"
                data-cy="focal"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.insurance.preAuthRef')}
                id="insurance-preAuthRef"
                name="preAuthRef"
                data-cy="preAuthRef"
                type="text"
              />
              <ValidatedField
                id="insurance-coverage"
                name="coverageId"
                data-cy="coverage"
                label={translate('hcpNphiesPortalApp.insurance.coverage')}
                type="select"
              >
                <option value="" key="0" />
                {coverages
                  ? coverages.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="insurance-claimResponse"
                name="claimResponseId"
                data-cy="claimResponse"
                label={translate('hcpNphiesPortalApp.insurance.claimResponse')}
                type="select"
              >
                <option value="" key="0" />
                {claimResponses
                  ? claimResponses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="insurance-claim"
                name="claimId"
                data-cy="claim"
                label={translate('hcpNphiesPortalApp.insurance.claim')}
                type="select"
              >
                <option value="" key="0" />
                {claims
                  ? claims.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/insurance" replace color="info">
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
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default InsuranceUpdate;
