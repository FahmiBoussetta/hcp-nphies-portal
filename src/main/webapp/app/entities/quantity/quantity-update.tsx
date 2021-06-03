import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './quantity.reducer';
import { IQuantity } from 'app/shared/model/quantity.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IQuantityUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const QuantityUpdate = (props: IQuantityUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { quantityEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/quantity');
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
        ...quantityEntity,
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
          <h2 id="hcpNphiesPortalApp.quantity.home.createOrEditLabel" data-cy="QuantityCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.quantity.home.createOrEditLabel">Create or edit a Quantity</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : quantityEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="quantity-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="quantity-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="valueLabel" for="quantity-value">
                  <Translate contentKey="hcpNphiesPortalApp.quantity.value">Value</Translate>
                </Label>
                <AvField id="quantity-value" data-cy="value" type="text" name="value" />
              </AvGroup>
              <AvGroup>
                <Label id="unitLabel" for="quantity-unit">
                  <Translate contentKey="hcpNphiesPortalApp.quantity.unit">Unit</Translate>
                </Label>
                <AvField id="quantity-unit" data-cy="unit" type="text" name="unit" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/quantity" replace color="info">
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
  quantityEntity: storeState.quantity.entity,
  loading: storeState.quantity.loading,
  updating: storeState.quantity.updating,
  updateSuccess: storeState.quantity.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(QuantityUpdate);
