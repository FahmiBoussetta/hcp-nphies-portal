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
import { ICoverageEligibilityResponse } from 'app/shared/model/coverage-eligibility-response.model';
import { getEntities as getCoverageEligibilityResponses } from 'app/entities/coverage-eligibility-response/coverage-eligibility-response.reducer';
import { getEntity, updateEntity, createEntity, reset } from './response-insurance.reducer';
import { IResponseInsurance } from 'app/shared/model/response-insurance.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IResponseInsuranceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ResponseInsuranceUpdate = (props: IResponseInsuranceUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { responseInsuranceEntity, coverages, coverageEligibilityResponses, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/response-insurance');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCoverages();
    props.getCoverageEligibilityResponses();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.benefitStart = convertDateTimeToServer(values.benefitStart);
    values.benefitEnd = convertDateTimeToServer(values.benefitEnd);

    if (errors.length === 0) {
      const entity = {
        ...responseInsuranceEntity,
        ...values,
        coverage: coverages.find(it => it.id.toString() === values.coverageId.toString()),
        coverageEligibilityResponse: coverageEligibilityResponses.find(
          it => it.id.toString() === values.coverageEligibilityResponseId.toString()
        ),
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
            <AvForm model={isNew ? {} : responseInsuranceEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="response-insurance-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="response-insurance-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="notInforceReasonLabel" for="response-insurance-notInforceReason">
                  <Translate contentKey="hcpNphiesPortalApp.responseInsurance.notInforceReason">Not Inforce Reason</Translate>
                </Label>
                <AvField id="response-insurance-notInforceReason" data-cy="notInforceReason" type="text" name="notInforceReason" />
              </AvGroup>
              <AvGroup check>
                <Label id="inforceLabel">
                  <AvInput id="response-insurance-inforce" data-cy="inforce" type="checkbox" className="form-check-input" name="inforce" />
                  <Translate contentKey="hcpNphiesPortalApp.responseInsurance.inforce">Inforce</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="benefitStartLabel" for="response-insurance-benefitStart">
                  <Translate contentKey="hcpNphiesPortalApp.responseInsurance.benefitStart">Benefit Start</Translate>
                </Label>
                <AvInput
                  id="response-insurance-benefitStart"
                  data-cy="benefitStart"
                  type="datetime-local"
                  className="form-control"
                  name="benefitStart"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.responseInsuranceEntity.benefitStart)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="benefitEndLabel" for="response-insurance-benefitEnd">
                  <Translate contentKey="hcpNphiesPortalApp.responseInsurance.benefitEnd">Benefit End</Translate>
                </Label>
                <AvInput
                  id="response-insurance-benefitEnd"
                  data-cy="benefitEnd"
                  type="datetime-local"
                  className="form-control"
                  name="benefitEnd"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.responseInsuranceEntity.benefitEnd)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="response-insurance-coverage">
                  <Translate contentKey="hcpNphiesPortalApp.responseInsurance.coverage">Coverage</Translate>
                </Label>
                <AvInput id="response-insurance-coverage" data-cy="coverage" type="select" className="form-control" name="coverageId">
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
                <Label for="response-insurance-coverageEligibilityResponse">
                  <Translate contentKey="hcpNphiesPortalApp.responseInsurance.coverageEligibilityResponse">
                    Coverage Eligibility Response
                  </Translate>
                </Label>
                <AvInput
                  id="response-insurance-coverageEligibilityResponse"
                  data-cy="coverageEligibilityResponse"
                  type="select"
                  className="form-control"
                  name="coverageEligibilityResponseId"
                >
                  <option value="" key="0" />
                  {coverageEligibilityResponses
                    ? coverageEligibilityResponses.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/response-insurance" replace color="info">
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
  coverageEligibilityResponses: storeState.coverageEligibilityResponse.entities,
  responseInsuranceEntity: storeState.responseInsurance.entity,
  loading: storeState.responseInsurance.loading,
  updating: storeState.responseInsurance.updating,
  updateSuccess: storeState.responseInsurance.updateSuccess,
});

const mapDispatchToProps = {
  getCoverages,
  getCoverageEligibilityResponses,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ResponseInsuranceUpdate);
