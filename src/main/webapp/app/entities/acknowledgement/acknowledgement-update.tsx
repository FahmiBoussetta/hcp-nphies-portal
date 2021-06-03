import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './acknowledgement.reducer';
import { IAcknowledgement } from 'app/shared/model/acknowledgement.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAcknowledgementUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AcknowledgementUpdate = (props: IAcknowledgementUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { acknowledgementEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/acknowledgement');
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
        ...acknowledgementEntity,
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
          <h2 id="hcpNphiesPortalApp.acknowledgement.home.createOrEditLabel" data-cy="AcknowledgementCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.acknowledgement.home.createOrEditLabel">Create or edit a Acknowledgement</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : acknowledgementEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="acknowledgement-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="acknowledgement-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="valueLabel" for="acknowledgement-value">
                  <Translate contentKey="hcpNphiesPortalApp.acknowledgement.value">Value</Translate>
                </Label>
                <AvField id="acknowledgement-value" data-cy="value" type="text" name="value" />
              </AvGroup>
              <AvGroup>
                <Label id="systemLabel" for="acknowledgement-system">
                  <Translate contentKey="hcpNphiesPortalApp.acknowledgement.system">System</Translate>
                </Label>
                <AvField id="acknowledgement-system" data-cy="system" type="text" name="system" />
              </AvGroup>
              <AvGroup>
                <Label id="parsedLabel" for="acknowledgement-parsed">
                  <Translate contentKey="hcpNphiesPortalApp.acknowledgement.parsed">Parsed</Translate>
                </Label>
                <AvField id="acknowledgement-parsed" data-cy="parsed" type="text" name="parsed" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/acknowledgement" replace color="info">
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
  acknowledgementEntity: storeState.acknowledgement.entity,
  loading: storeState.acknowledgement.loading,
  updating: storeState.acknowledgement.updating,
  updateSuccess: storeState.acknowledgement.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AcknowledgementUpdate);
