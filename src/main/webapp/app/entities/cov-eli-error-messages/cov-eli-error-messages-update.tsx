import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICoverageEligibilityRequest } from 'app/shared/model/coverage-eligibility-request.model';
import { getEntities as getCoverageEligibilityRequests } from 'app/entities/coverage-eligibility-request/coverage-eligibility-request.reducer';
import { getEntity, updateEntity, createEntity, reset } from './cov-eli-error-messages.reducer';
import { ICovEliErrorMessages } from 'app/shared/model/cov-eli-error-messages.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICovEliErrorMessagesUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CovEliErrorMessagesUpdate = (props: ICovEliErrorMessagesUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { covEliErrorMessagesEntity, coverageEligibilityRequests, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/cov-eli-error-messages');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCoverageEligibilityRequests();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...covEliErrorMessagesEntity,
        ...values,
        coverageEligibilityRequest: coverageEligibilityRequests.find(
          it => it.id.toString() === values.coverageEligibilityRequestId.toString()
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
          <h2 id="hcpNphiesPortalApp.covEliErrorMessages.home.createOrEditLabel" data-cy="CovEliErrorMessagesCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.covEliErrorMessages.home.createOrEditLabel">
              Create or edit a CovEliErrorMessages
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : covEliErrorMessagesEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="cov-eli-error-messages-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="cov-eli-error-messages-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="messageLabel" for="cov-eli-error-messages-message">
                  <Translate contentKey="hcpNphiesPortalApp.covEliErrorMessages.message">Message</Translate>
                </Label>
                <AvField id="cov-eli-error-messages-message" data-cy="message" type="text" name="message" />
              </AvGroup>
              <AvGroup>
                <Label for="cov-eli-error-messages-coverageEligibilityRequest">
                  <Translate contentKey="hcpNphiesPortalApp.covEliErrorMessages.coverageEligibilityRequest">
                    Coverage Eligibility Request
                  </Translate>
                </Label>
                <AvInput
                  id="cov-eli-error-messages-coverageEligibilityRequest"
                  data-cy="coverageEligibilityRequest"
                  type="select"
                  className="form-control"
                  name="coverageEligibilityRequestId"
                >
                  <option value="" key="0" />
                  {coverageEligibilityRequests
                    ? coverageEligibilityRequests.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/cov-eli-error-messages" replace color="info">
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
  coverageEligibilityRequests: storeState.coverageEligibilityRequest.entities,
  covEliErrorMessagesEntity: storeState.covEliErrorMessages.entity,
  loading: storeState.covEliErrorMessages.loading,
  updating: storeState.covEliErrorMessages.updating,
  updateSuccess: storeState.covEliErrorMessages.updateSuccess,
});

const mapDispatchToProps = {
  getCoverageEligibilityRequests,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CovEliErrorMessagesUpdate);
