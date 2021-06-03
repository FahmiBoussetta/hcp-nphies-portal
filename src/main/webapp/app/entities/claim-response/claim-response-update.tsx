import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './claim-response.reducer';
import { IClaimResponse } from 'app/shared/model/claim-response.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IClaimResponseUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ClaimResponseUpdate = (props: IClaimResponseUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { claimResponseEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/claim-response');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...claimResponseEntity,
        ...values,
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
          <h2 id="hcpNphiesPortalApp.claimResponse.home.createOrEditLabel" data-cy="ClaimResponseCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.claimResponse.home.createOrEditLabel">Create or edit a ClaimResponse</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : claimResponseEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="claim-response-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="claim-response-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="valueLabel" for="claim-response-value">
                  <Translate contentKey="hcpNphiesPortalApp.claimResponse.value">Value</Translate>
                </Label>
                <AvField id="claim-response-value" data-cy="value" type="text" name="value" />
              </AvGroup>
              <AvGroup>
                <Label id="systemLabel" for="claim-response-system">
                  <Translate contentKey="hcpNphiesPortalApp.claimResponse.system">System</Translate>
                </Label>
                <AvField id="claim-response-system" data-cy="system" type="text" name="system" />
              </AvGroup>
              <AvGroup>
                <Label id="parsedLabel" for="claim-response-parsed">
                  <Translate contentKey="hcpNphiesPortalApp.claimResponse.parsed">Parsed</Translate>
                </Label>
                <AvField id="claim-response-parsed" data-cy="parsed" type="text" name="parsed" />
              </AvGroup>
              <AvGroup>
                <Label id="outcomeLabel" for="claim-response-outcome">
                  <Translate contentKey="hcpNphiesPortalApp.claimResponse.outcome">Outcome</Translate>
                </Label>
                <AvField id="claim-response-outcome" data-cy="outcome" type="text" name="outcome" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/claim-response" replace color="info">
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
  claimResponseEntity: storeState.claimResponse.entity,
  loading: storeState.claimResponse.loading,
  updating: storeState.claimResponse.updating,
  updateSuccess: storeState.claimResponse.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClaimResponseUpdate);
