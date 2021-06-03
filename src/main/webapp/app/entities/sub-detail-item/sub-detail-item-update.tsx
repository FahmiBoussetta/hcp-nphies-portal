import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IDetailItem } from 'app/shared/model/detail-item.model';
import { getEntities as getDetailItems } from 'app/entities/detail-item/detail-item.reducer';
import { getEntity, updateEntity, createEntity, reset } from './sub-detail-item.reducer';
import { ISubDetailItem } from 'app/shared/model/sub-detail-item.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISubDetailItemUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SubDetailItemUpdate = (props: ISubDetailItemUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { subDetailItemEntity, detailItems, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/sub-detail-item');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getDetailItems();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...subDetailItemEntity,
        ...values,
        detailItem: detailItems.find(it => it.id.toString() === values.detailItemId.toString()),
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
          <h2 id="hcpNphiesPortalApp.subDetailItem.home.createOrEditLabel" data-cy="SubDetailItemCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.subDetailItem.home.createOrEditLabel">Create or edit a SubDetailItem</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : subDetailItemEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="sub-detail-item-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="sub-detail-item-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="sequenceLabel" for="sub-detail-item-sequence">
                  <Translate contentKey="hcpNphiesPortalApp.subDetailItem.sequence">Sequence</Translate>
                </Label>
                <AvField
                  id="sub-detail-item-sequence"
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
                <Label id="taxLabel" for="sub-detail-item-tax">
                  <Translate contentKey="hcpNphiesPortalApp.subDetailItem.tax">Tax</Translate>
                </Label>
                <AvField id="sub-detail-item-tax" data-cy="tax" type="text" name="tax" />
              </AvGroup>
              <AvGroup>
                <Label id="transportationSRCALabel" for="sub-detail-item-transportationSRCA">
                  <Translate contentKey="hcpNphiesPortalApp.subDetailItem.transportationSRCA">Transportation SRCA</Translate>
                </Label>
                <AvField id="sub-detail-item-transportationSRCA" data-cy="transportationSRCA" type="text" name="transportationSRCA" />
              </AvGroup>
              <AvGroup>
                <Label id="imagingLabel" for="sub-detail-item-imaging">
                  <Translate contentKey="hcpNphiesPortalApp.subDetailItem.imaging">Imaging</Translate>
                </Label>
                <AvField id="sub-detail-item-imaging" data-cy="imaging" type="text" name="imaging" />
              </AvGroup>
              <AvGroup>
                <Label id="laboratoryLabel" for="sub-detail-item-laboratory">
                  <Translate contentKey="hcpNphiesPortalApp.subDetailItem.laboratory">Laboratory</Translate>
                </Label>
                <AvField id="sub-detail-item-laboratory" data-cy="laboratory" type="text" name="laboratory" />
              </AvGroup>
              <AvGroup>
                <Label id="medicalDeviceLabel" for="sub-detail-item-medicalDevice">
                  <Translate contentKey="hcpNphiesPortalApp.subDetailItem.medicalDevice">Medical Device</Translate>
                </Label>
                <AvField id="sub-detail-item-medicalDevice" data-cy="medicalDevice" type="text" name="medicalDevice" />
              </AvGroup>
              <AvGroup>
                <Label id="oralHealthIPLabel" for="sub-detail-item-oralHealthIP">
                  <Translate contentKey="hcpNphiesPortalApp.subDetailItem.oralHealthIP">Oral Health IP</Translate>
                </Label>
                <AvField id="sub-detail-item-oralHealthIP" data-cy="oralHealthIP" type="text" name="oralHealthIP" />
              </AvGroup>
              <AvGroup>
                <Label id="oralHealthOPLabel" for="sub-detail-item-oralHealthOP">
                  <Translate contentKey="hcpNphiesPortalApp.subDetailItem.oralHealthOP">Oral Health OP</Translate>
                </Label>
                <AvField id="sub-detail-item-oralHealthOP" data-cy="oralHealthOP" type="text" name="oralHealthOP" />
              </AvGroup>
              <AvGroup>
                <Label id="procedureLabel" for="sub-detail-item-procedure">
                  <Translate contentKey="hcpNphiesPortalApp.subDetailItem.procedure">Procedure</Translate>
                </Label>
                <AvField id="sub-detail-item-procedure" data-cy="procedure" type="text" name="procedure" />
              </AvGroup>
              <AvGroup>
                <Label id="servicesLabel" for="sub-detail-item-services">
                  <Translate contentKey="hcpNphiesPortalApp.subDetailItem.services">Services</Translate>
                </Label>
                <AvField id="sub-detail-item-services" data-cy="services" type="text" name="services" />
              </AvGroup>
              <AvGroup>
                <Label id="medicationCodeLabel" for="sub-detail-item-medicationCode">
                  <Translate contentKey="hcpNphiesPortalApp.subDetailItem.medicationCode">Medication Code</Translate>
                </Label>
                <AvField id="sub-detail-item-medicationCode" data-cy="medicationCode" type="text" name="medicationCode" />
              </AvGroup>
              <AvGroup>
                <Label id="quantityLabel" for="sub-detail-item-quantity">
                  <Translate contentKey="hcpNphiesPortalApp.subDetailItem.quantity">Quantity</Translate>
                </Label>
                <AvField
                  id="sub-detail-item-quantity"
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
                <Label id="unitPriceLabel" for="sub-detail-item-unitPrice">
                  <Translate contentKey="hcpNphiesPortalApp.subDetailItem.unitPrice">Unit Price</Translate>
                </Label>
                <AvField
                  id="sub-detail-item-unitPrice"
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
                <Label for="sub-detail-item-detailItem">
                  <Translate contentKey="hcpNphiesPortalApp.subDetailItem.detailItem">Detail Item</Translate>
                </Label>
                <AvInput id="sub-detail-item-detailItem" data-cy="detailItem" type="select" className="form-control" name="detailItemId">
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
              <Button tag={Link} id="cancel-save" to="/sub-detail-item" replace color="info">
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
  detailItems: storeState.detailItem.entities,
  subDetailItemEntity: storeState.subDetailItem.entity,
  loading: storeState.subDetailItem.loading,
  updating: storeState.subDetailItem.updating,
  updateSuccess: storeState.subDetailItem.updateSuccess,
});

const mapDispatchToProps = {
  getDetailItems,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SubDetailItemUpdate);
