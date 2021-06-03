import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './operation-outcome.reducer';
import { IOperationOutcome } from 'app/shared/model/operation-outcome.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IOperationOutcomeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const OperationOutcomeUpdate = (props: IOperationOutcomeUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { operationOutcomeEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/operation-outcome');
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
        ...operationOutcomeEntity,
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
          <h2 id="hcpNphiesPortalApp.operationOutcome.home.createOrEditLabel" data-cy="OperationOutcomeCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.operationOutcome.home.createOrEditLabel">Create or edit a OperationOutcome</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : operationOutcomeEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="operation-outcome-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="operation-outcome-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="valueLabel" for="operation-outcome-value">
                  <Translate contentKey="hcpNphiesPortalApp.operationOutcome.value">Value</Translate>
                </Label>
                <AvField id="operation-outcome-value" data-cy="value" type="text" name="value" />
              </AvGroup>
              <AvGroup>
                <Label id="systemLabel" for="operation-outcome-system">
                  <Translate contentKey="hcpNphiesPortalApp.operationOutcome.system">System</Translate>
                </Label>
                <AvField id="operation-outcome-system" data-cy="system" type="text" name="system" />
              </AvGroup>
              <AvGroup>
                <Label id="parsedLabel" for="operation-outcome-parsed">
                  <Translate contentKey="hcpNphiesPortalApp.operationOutcome.parsed">Parsed</Translate>
                </Label>
                <AvField id="operation-outcome-parsed" data-cy="parsed" type="text" name="parsed" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/operation-outcome" replace color="info">
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
  operationOutcomeEntity: storeState.operationOutcome.entity,
  loading: storeState.operationOutcome.loading,
  updating: storeState.operationOutcome.updating,
  updateSuccess: storeState.operationOutcome.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(OperationOutcomeUpdate);
