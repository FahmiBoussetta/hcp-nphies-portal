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
import { getEntity, updateEntity, createEntity, reset } from './detail-item.reducer';
import { IDetailItem } from 'app/shared/model/detail-item.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDetailItemUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DetailItemUpdate = (props: IDetailItemUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { detailItemEntity, items, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/detail-item');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getItems();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...detailItemEntity,
        ...values,
        item: items.find(it => it.id.toString() === values.itemId.toString()),
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
          <h2 id="hcpNphiesPortalApp.detailItem.home.createOrEditLabel" data-cy="DetailItemCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.detailItem.home.createOrEditLabel">Create or edit a DetailItem</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : detailItemEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="detail-item-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="detail-item-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="sequenceLabel" for="detail-item-sequence">
                  <Translate contentKey="hcpNphiesPortalApp.detailItem.sequence">Sequence</Translate>
                </Label>
                <AvField
                  id="detail-item-sequence"
                  data-cy="sequence"
                  type="string"
                  className="form-control"
                  name="sequence"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="taxLabel" for="detail-item-tax">
                  <Translate contentKey="hcpNphiesPortalApp.detailItem.tax">Tax</Translate>
                </Label>
                <AvField id="detail-item-tax" data-cy="tax" type="text" name="tax" />
              </AvGroup>
              <AvGroup>
                <Label id="transportationSRCALabel" for="detail-item-transportationSRCA">
                  <Translate contentKey="hcpNphiesPortalApp.detailItem.transportationSRCA">Transportation SRCA</Translate>
                </Label>
                <AvField id="detail-item-transportationSRCA" data-cy="transportationSRCA" type="text" name="transportationSRCA" />
              </AvGroup>
              <AvGroup>
                <Label id="imagingLabel" for="detail-item-imaging">
                  <Translate contentKey="hcpNphiesPortalApp.detailItem.imaging">Imaging</Translate>
                </Label>
                <AvField id="detail-item-imaging" data-cy="imaging" type="text" name="imaging" />
              </AvGroup>
              <AvGroup>
                <Label id="laboratoryLabel" for="detail-item-laboratory">
                  <Translate contentKey="hcpNphiesPortalApp.detailItem.laboratory">Laboratory</Translate>
                </Label>
                <AvField id="detail-item-laboratory" data-cy="laboratory" type="text" name="laboratory" />
              </AvGroup>
              <AvGroup>
                <Label id="medicalDeviceLabel" for="detail-item-medicalDevice">
                  <Translate contentKey="hcpNphiesPortalApp.detailItem.medicalDevice">Medical Device</Translate>
                </Label>
                <AvField id="detail-item-medicalDevice" data-cy="medicalDevice" type="text" name="medicalDevice" />
              </AvGroup>
              <AvGroup>
                <Label id="oralHealthIPLabel" for="detail-item-oralHealthIP">
                  <Translate contentKey="hcpNphiesPortalApp.detailItem.oralHealthIP">Oral Health IP</Translate>
                </Label>
                <AvField id="detail-item-oralHealthIP" data-cy="oralHealthIP" type="text" name="oralHealthIP" />
              </AvGroup>
              <AvGroup>
                <Label id="oralHealthOPLabel" for="detail-item-oralHealthOP">
                  <Translate contentKey="hcpNphiesPortalApp.detailItem.oralHealthOP">Oral Health OP</Translate>
                </Label>
                <AvField id="detail-item-oralHealthOP" data-cy="oralHealthOP" type="text" name="oralHealthOP" />
              </AvGroup>
              <AvGroup>
                <Label id="procedureLabel" for="detail-item-procedure">
                  <Translate contentKey="hcpNphiesPortalApp.detailItem.procedure">Procedure</Translate>
                </Label>
                <AvField id="detail-item-procedure" data-cy="procedure" type="text" name="procedure" />
              </AvGroup>
              <AvGroup>
                <Label id="servicesLabel" for="detail-item-services">
                  <Translate contentKey="hcpNphiesPortalApp.detailItem.services">Services</Translate>
                </Label>
                <AvField id="detail-item-services" data-cy="services" type="text" name="services" />
              </AvGroup>
              <AvGroup>
                <Label id="medicationCodeLabel" for="detail-item-medicationCode">
                  <Translate contentKey="hcpNphiesPortalApp.detailItem.medicationCode">Medication Code</Translate>
                </Label>
                <AvField id="detail-item-medicationCode" data-cy="medicationCode" type="text" name="medicationCode" />
              </AvGroup>
              <AvGroup>
                <Label id="quantityLabel" for="detail-item-quantity">
                  <Translate contentKey="hcpNphiesPortalApp.detailItem.quantity">Quantity</Translate>
                </Label>
                <AvField
                  id="detail-item-quantity"
                  data-cy="quantity"
                  type="string"
                  className="form-control"
                  name="quantity"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="unitPriceLabel" for="detail-item-unitPrice">
                  <Translate contentKey="hcpNphiesPortalApp.detailItem.unitPrice">Unit Price</Translate>
                </Label>
                <AvField
                  id="detail-item-unitPrice"
                  data-cy="unitPrice"
                  type="string"
                  className="form-control"
                  name="unitPrice"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="detail-item-item">
                  <Translate contentKey="hcpNphiesPortalApp.detailItem.item">Item</Translate>
                </Label>
                <AvInput id="detail-item-item" data-cy="item" type="select" className="form-control" name="itemId">
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
              <Button tag={Link} id="cancel-save" to="/detail-item" replace color="info">
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
  detailItemEntity: storeState.detailItem.entity,
  loading: storeState.detailItem.loading,
  updating: storeState.detailItem.updating,
  updateSuccess: storeState.detailItem.updateSuccess,
});

const mapDispatchToProps = {
  getItems,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DetailItemUpdate);
