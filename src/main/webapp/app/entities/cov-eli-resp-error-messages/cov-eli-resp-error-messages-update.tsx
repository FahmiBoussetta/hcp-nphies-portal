import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICoverageEligibilityResponse } from 'app/shared/model/coverage-eligibility-response.model';
import { getEntities as getCoverageEligibilityResponses } from 'app/entities/coverage-eligibility-response/coverage-eligibility-response.reducer';
import { getEntity, updateEntity, createEntity, reset } from './cov-eli-resp-error-messages.reducer';
import { ICovEliRespErrorMessages } from 'app/shared/model/cov-eli-resp-error-messages.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICovEliRespErrorMessagesUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CovEliRespErrorMessagesUpdate = (props: ICovEliRespErrorMessagesUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { covEliRespErrorMessagesEntity, coverageEligibilityResponses, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/cov-eli-resp-error-messages');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCoverageEligibilityResponses();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...covEliRespErrorMessagesEntity,
        ...values,
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
          <h2 id="hcpNphiesPortalApp.covEliRespErrorMessages.home.createOrEditLabel" data-cy="CovEliRespErrorMessagesCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.covEliRespErrorMessages.home.createOrEditLabel">
              Create or edit a CovEliRespErrorMessages
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : covEliRespErrorMessagesEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="cov-eli-resp-error-messages-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="cov-eli-resp-error-messages-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="messageLabel" for="cov-eli-resp-error-messages-message">
                  <Translate contentKey="hcpNphiesPortalApp.covEliRespErrorMessages.message">Message</Translate>
                </Label>
                <AvField id="cov-eli-resp-error-messages-message" data-cy="message" type="text" name="message" />
              </AvGroup>
              <AvGroup>
                <Label for="cov-eli-resp-error-messages-coverageEligibilityResponse">
                  <Translate contentKey="hcpNphiesPortalApp.covEliRespErrorMessages.coverageEligibilityResponse">
                    Coverage Eligibility Response
                  </Translate>
                </Label>
                <AvInput
                  id="cov-eli-resp-error-messages-coverageEligibilityResponse"
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
              <Button tag={Link} id="cancel-save" to="/cov-eli-resp-error-messages" replace color="info">
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
  coverageEligibilityResponses: storeState.coverageEligibilityResponse.entities,
  covEliRespErrorMessagesEntity: storeState.covEliRespErrorMessages.entity,
  loading: storeState.covEliRespErrorMessages.loading,
  updating: storeState.covEliRespErrorMessages.updating,
  updateSuccess: storeState.covEliRespErrorMessages.updateSuccess,
});

const mapDispatchToProps = {
  getCoverageEligibilityResponses,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CovEliRespErrorMessagesUpdate);
