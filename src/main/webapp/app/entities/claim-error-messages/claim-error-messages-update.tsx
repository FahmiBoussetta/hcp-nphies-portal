import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IClaim } from 'app/shared/model/claim.model';
import { getEntities as getClaims } from 'app/entities/claim/claim.reducer';
import { getEntity, updateEntity, createEntity, reset } from './claim-error-messages.reducer';
import { IClaimErrorMessages } from 'app/shared/model/claim-error-messages.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IClaimErrorMessagesUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ClaimErrorMessagesUpdate = (props: IClaimErrorMessagesUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { claimErrorMessagesEntity, claims, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/claim-error-messages');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

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
        ...claimErrorMessagesEntity,
        ...values,
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
          <h2 id="hcpNphiesPortalApp.claimErrorMessages.home.createOrEditLabel" data-cy="ClaimErrorMessagesCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.claimErrorMessages.home.createOrEditLabel">
              Create or edit a ClaimErrorMessages
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : claimErrorMessagesEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="claim-error-messages-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="claim-error-messages-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="messageLabel" for="claim-error-messages-message">
                  <Translate contentKey="hcpNphiesPortalApp.claimErrorMessages.message">Message</Translate>
                </Label>
                <AvField id="claim-error-messages-message" data-cy="message" type="text" name="message" />
              </AvGroup>
              <AvGroup>
                <Label for="claim-error-messages-claim">
                  <Translate contentKey="hcpNphiesPortalApp.claimErrorMessages.claim">Claim</Translate>
                </Label>
                <AvInput id="claim-error-messages-claim" data-cy="claim" type="select" className="form-control" name="claimId">
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
              <Button tag={Link} id="cancel-save" to="/claim-error-messages" replace color="info">
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
  claims: storeState.claim.entities,
  claimErrorMessagesEntity: storeState.claimErrorMessages.entity,
  loading: storeState.claimErrorMessages.loading,
  updating: storeState.claimErrorMessages.updating,
  updateSuccess: storeState.claimErrorMessages.updateSuccess,
});

const mapDispatchToProps = {
  getClaims,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClaimErrorMessagesUpdate);
