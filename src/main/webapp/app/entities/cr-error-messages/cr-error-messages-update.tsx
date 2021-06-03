import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IClaimResponse } from 'app/shared/model/claim-response.model';
import { getEntities as getClaimResponses } from 'app/entities/claim-response/claim-response.reducer';
import { getEntity, updateEntity, createEntity, reset } from './cr-error-messages.reducer';
import { ICRErrorMessages } from 'app/shared/model/cr-error-messages.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICRErrorMessagesUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CRErrorMessagesUpdate = (props: ICRErrorMessagesUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { cRErrorMessagesEntity, claimResponses, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/cr-error-messages');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getClaimResponses();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...cRErrorMessagesEntity,
        ...values,
        claimResponse: claimResponses.find(it => it.id.toString() === values.claimResponseId.toString()),
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
          <h2 id="hcpNphiesPortalApp.cRErrorMessages.home.createOrEditLabel" data-cy="CRErrorMessagesCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.cRErrorMessages.home.createOrEditLabel">Create or edit a CRErrorMessages</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : cRErrorMessagesEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="cr-error-messages-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="cr-error-messages-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="messageLabel" for="cr-error-messages-message">
                  <Translate contentKey="hcpNphiesPortalApp.cRErrorMessages.message">Message</Translate>
                </Label>
                <AvField id="cr-error-messages-message" data-cy="message" type="text" name="message" />
              </AvGroup>
              <AvGroup>
                <Label for="cr-error-messages-claimResponse">
                  <Translate contentKey="hcpNphiesPortalApp.cRErrorMessages.claimResponse">Claim Response</Translate>
                </Label>
                <AvInput
                  id="cr-error-messages-claimResponse"
                  data-cy="claimResponse"
                  type="select"
                  className="form-control"
                  name="claimResponseId"
                >
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
              <Button tag={Link} id="cancel-save" to="/cr-error-messages" replace color="info">
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
  claimResponses: storeState.claimResponse.entities,
  cRErrorMessagesEntity: storeState.cRErrorMessages.entity,
  loading: storeState.cRErrorMessages.loading,
  updating: storeState.cRErrorMessages.updating,
  updateSuccess: storeState.cRErrorMessages.updateSuccess,
});

const mapDispatchToProps = {
  getClaimResponses,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CRErrorMessagesUpdate);
