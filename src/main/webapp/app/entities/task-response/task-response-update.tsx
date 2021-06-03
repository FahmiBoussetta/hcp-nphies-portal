import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './task-response.reducer';
import { ITaskResponse } from 'app/shared/model/task-response.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITaskResponseUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TaskResponseUpdate = (props: ITaskResponseUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { taskResponseEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/task-response');
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
        ...taskResponseEntity,
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
          <h2 id="hcpNphiesPortalApp.taskResponse.home.createOrEditLabel" data-cy="TaskResponseCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.taskResponse.home.createOrEditLabel">Create or edit a TaskResponse</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : taskResponseEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="task-response-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="task-response-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="valueLabel" for="task-response-value">
                  <Translate contentKey="hcpNphiesPortalApp.taskResponse.value">Value</Translate>
                </Label>
                <AvField id="task-response-value" data-cy="value" type="text" name="value" />
              </AvGroup>
              <AvGroup>
                <Label id="systemLabel" for="task-response-system">
                  <Translate contentKey="hcpNphiesPortalApp.taskResponse.system">System</Translate>
                </Label>
                <AvField id="task-response-system" data-cy="system" type="text" name="system" />
              </AvGroup>
              <AvGroup>
                <Label id="parsedLabel" for="task-response-parsed">
                  <Translate contentKey="hcpNphiesPortalApp.taskResponse.parsed">Parsed</Translate>
                </Label>
                <AvField id="task-response-parsed" data-cy="parsed" type="text" name="parsed" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="task-response-status">
                  <Translate contentKey="hcpNphiesPortalApp.taskResponse.status">Status</Translate>
                </Label>
                <AvField id="task-response-status" data-cy="status" type="text" name="status" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/task-response" replace color="info">
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
  taskResponseEntity: storeState.taskResponse.entity,
  loading: storeState.taskResponse.loading,
  updating: storeState.taskResponse.updating,
  updateSuccess: storeState.taskResponse.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TaskResponseUpdate);
