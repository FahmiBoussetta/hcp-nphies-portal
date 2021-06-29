import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICoverage } from 'app/shared/model/coverage.model';
import { getEntities as getCoverages } from 'app/entities/coverage/coverage.reducer';
import { ICoverageEligibilityResponse } from 'app/shared/model/coverage-eligibility-response.model';
import { getEntities as getCoverageEligibilityResponses } from 'app/entities/coverage-eligibility-response/coverage-eligibility-response.reducer';
import { getEntity, updateEntity, createEntity, reset } from './response-insurance.reducer';
import { IResponseInsurance } from 'app/shared/model/response-insurance.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ResponseInsuranceUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const coverages = useAppSelector(state => state.coverage.entities);
  const coverageEligibilityResponses = useAppSelector(state => state.coverageEligibilityResponse.entities);
  const responseInsuranceEntity = useAppSelector(state => state.responseInsurance.entity);
  const loading = useAppSelector(state => state.responseInsurance.loading);
  const updating = useAppSelector(state => state.responseInsurance.updating);
  const updateSuccess = useAppSelector(state => state.responseInsurance.updateSuccess);

  const handleClose = () => {
    props.history.push('/response-insurance');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCoverages({}));
    dispatch(getCoverageEligibilityResponses({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.benefitStart = convertDateTimeToServer(values.benefitStart);
    values.benefitEnd = convertDateTimeToServer(values.benefitEnd);

    const entity = {
      ...responseInsuranceEntity,
      ...values,
      coverage: coverages.find(it => it.id.toString() === values.coverageId.toString()),
      coverageEligibilityResponse: coverageEligibilityResponses.find(
        it => it.id.toString() === values.coverageEligibilityResponseId.toString()
      ),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          benefitStart: displayDefaultDateTime(),
          benefitEnd: displayDefaultDateTime(),
        }
      : {
          ...responseInsuranceEntity,
          benefitStart: convertDateTimeFromServer(responseInsuranceEntity.benefitStart),
          benefitEnd: convertDateTimeFromServer(responseInsuranceEntity.benefitEnd),
          coverageId: responseInsuranceEntity?.coverage?.id,
          coverageEligibilityResponseId: responseInsuranceEntity?.coverageEligibilityResponse?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.responseInsurance.home.createOrEditLabel" data-cy="ResponseInsuranceCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.responseInsurance.home.createOrEditLabel">
              Create or edit a ResponseInsurance
            </Translate>
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
                  id="response-insurance-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.responseInsurance.notInforceReason')}
                id="response-insurance-notInforceReason"
                name="notInforceReason"
                data-cy="notInforceReason"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.responseInsurance.inforce')}
                id="response-insurance-inforce"
                name="inforce"
                data-cy="inforce"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.responseInsurance.benefitStart')}
                id="response-insurance-benefitStart"
                name="benefitStart"
                data-cy="benefitStart"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.responseInsurance.benefitEnd')}
                id="response-insurance-benefitEnd"
                name="benefitEnd"
                data-cy="benefitEnd"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="response-insurance-coverage"
                name="coverageId"
                data-cy="coverage"
                label={translate('hcpNphiesPortalApp.responseInsurance.coverage')}
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
                id="response-insurance-coverageEligibilityResponse"
                name="coverageEligibilityResponseId"
                data-cy="coverageEligibilityResponse"
                label={translate('hcpNphiesPortalApp.responseInsurance.coverageEligibilityResponse')}
                type="select"
              >
                <option value="" key="0" />
                {coverageEligibilityResponses
                  ? coverageEligibilityResponses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/response-insurance" replace color="info">
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

export default ResponseInsuranceUpdate;
