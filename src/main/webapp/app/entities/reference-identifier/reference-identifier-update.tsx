import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IItem } from 'app/shared/model/item.model';
import { getEntities as getItems } from 'app/entities/item/item.reducer';
import { IDetailItem } from 'app/shared/model/detail-item.model';
import { getEntities as getDetailItems } from 'app/entities/detail-item/detail-item.reducer';
import { ISubDetailItem } from 'app/shared/model/sub-detail-item.model';
import { getEntities as getSubDetailItems } from 'app/entities/sub-detail-item/sub-detail-item.reducer';
import { getEntity, updateEntity, createEntity, reset } from './reference-identifier.reducer';
import { IReferenceIdentifier } from 'app/shared/model/reference-identifier.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IReferenceIdentifierUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ReferenceIdentifierUpdate = (props: IReferenceIdentifierUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { referenceIdentifierEntity, items, detailItems, subDetailItems, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/reference-identifier');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getItems();
    props.getDetailItems();
    props.getSubDetailItems();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...referenceIdentifierEntity,
        ...values,
        item: items.find(it => it.id.toString() === values.itemId.toString()),
        detailItem: detailItems.find(it => it.id.toString() === values.detailItemId.toString()),
        subDetailItem: subDetailItems.find(it => it.id.toString() === values.subDetailItemId.toString()),
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
          <h2 id="hcpNphiesPortalApp.referenceIdentifier.home.createOrEditLabel" data-cy="ReferenceIdentifierCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.referenceIdentifier.home.createOrEditLabel">
              Create or edit a ReferenceIdentifier
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : referenceIdentifierEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="reference-identifier-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="reference-identifier-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="refLabel" for="reference-identifier-ref">
                  <Translate contentKey="hcpNphiesPortalApp.referenceIdentifier.ref">Ref</Translate>
                </Label>
                <AvField id="reference-identifier-ref" data-cy="ref" type="text" name="ref" />
              </AvGroup>
              <AvGroup>
                <Label id="idValueLabel" for="reference-identifier-idValue">
                  <Translate contentKey="hcpNphiesPortalApp.referenceIdentifier.idValue">Id Value</Translate>
                </Label>
                <AvField id="reference-identifier-idValue" data-cy="idValue" type="text" name="idValue" />
              </AvGroup>
              <AvGroup>
                <Label id="identifierLabel" for="reference-identifier-identifier">
                  <Translate contentKey="hcpNphiesPortalApp.referenceIdentifier.identifier">Identifier</Translate>
                </Label>
                <AvField id="reference-identifier-identifier" data-cy="identifier" type="text" name="identifier" />
              </AvGroup>
              <AvGroup>
                <Label id="displayLabel" for="reference-identifier-display">
                  <Translate contentKey="hcpNphiesPortalApp.referenceIdentifier.display">Display</Translate>
                </Label>
                <AvField id="reference-identifier-display" data-cy="display" type="text" name="display" />
              </AvGroup>
              <AvGroup>
                <Label for="reference-identifier-item">
                  <Translate contentKey="hcpNphiesPortalApp.referenceIdentifier.item">Item</Translate>
                </Label>
                <AvInput id="reference-identifier-item" data-cy="item" type="select" className="form-control" name="itemId">
                  <option value="" key="0" />
                  {items
                    ? items.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="reference-identifier-detailItem">
                  <Translate contentKey="hcpNphiesPortalApp.referenceIdentifier.detailItem">Detail Item</Translate>
                </Label>
                <AvInput
                  id="reference-identifier-detailItem"
                  data-cy="detailItem"
                  type="select"
                  className="form-control"
                  name="detailItemId"
                >
                  <option value="" key="0" />
                  {detailItems
                    ? detailItems.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="reference-identifier-subDetailItem">
                  <Translate contentKey="hcpNphiesPortalApp.referenceIdentifier.subDetailItem">Sub Detail Item</Translate>
                </Label>
                <AvInput
                  id="reference-identifier-subDetailItem"
                  data-cy="subDetailItem"
                  type="select"
                  className="form-control"
                  name="subDetailItemId"
                >
                  <option value="" key="0" />
                  {subDetailItems
                    ? subDetailItems.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/reference-identifier" replace color="info">
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
  items: storeState.item.entities,
  detailItems: storeState.detailItem.entities,
  subDetailItems: storeState.subDetailItem.entities,
  referenceIdentifierEntity: storeState.referenceIdentifier.entity,
  loading: storeState.referenceIdentifier.loading,
  updating: storeState.referenceIdentifier.updating,
  updateSuccess: storeState.referenceIdentifier.updateSuccess,
});

const mapDispatchToProps = {
  getItems,
  getDetailItems,
  getSubDetailItems,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ReferenceIdentifierUpdate);
