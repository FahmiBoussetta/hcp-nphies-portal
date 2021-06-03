import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IReferenceIdentifier } from 'app/shared/model/reference-identifier.model';
import { getEntities as getReferenceIdentifiers } from 'app/entities/reference-identifier/reference-identifier.reducer';
import { IClaim } from 'app/shared/model/claim.model';
import { getEntities as getClaims } from 'app/entities/claim/claim.reducer';
import { getEntity, updateEntity, createEntity, reset } from './related.reducer';
import { IRelated } from 'app/shared/model/related.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRelatedUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RelatedUpdate = (props: IRelatedUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { relatedEntity, referenceIdentifiers, claims, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/related');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getReferenceIdentifiers();
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
        ...relatedEntity,
        ...values,
        claimReference: referenceIdentifiers.find(it => it.id.toString() === values.claimReferenceId.toString()),
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
          <h2 id="hcpNphiesPortalApp.related.home.createOrEditLabel" data-cy="RelatedCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.related.home.createOrEditLabel">Create or edit a Related</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : relatedEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="related-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="related-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="relationShipLabel" for="related-relationShip">
                  <Translate contentKey="hcpNphiesPortalApp.related.relationShip">Relation Ship</Translate>
                </Label>
                <AvInput
                  id="related-relationShip"
                  data-cy="relationShip"
                  type="select"
                  className="form-control"
                  name="relationShip"
                  value={(!isNew && relatedEntity.relationShip) || 'Prior'}
                >
                  <option value="Prior">{translate('hcpNphiesPortalApp.ClaimRelationshipEnum.Prior')}</option>
                  <option value="Associated">{translate('hcpNphiesPortalApp.ClaimRelationshipEnum.Associated')}</option>
                  <option value="Extend">{translate('hcpNphiesPortalApp.ClaimRelationshipEnum.Extend')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="related-claimReference">
                  <Translate contentKey="hcpNphiesPortalApp.related.claimReference">Claim Reference</Translate>
                </Label>
                <AvInput
                  id="related-claimReference"
                  data-cy="claimReference"
                  type="select"
                  className="form-control"
                  name="claimReferenceId"
                >
                  <option value="" key="0" />
                  {referenceIdentifiers
                    ? referenceIdentifiers.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="related-claim">
                  <Translate contentKey="hcpNphiesPortalApp.related.claim">Claim</Translate>
                </Label>
                <AvInput id="related-claim" data-cy="claim" type="select" className="form-control" name="claimId">
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
              <Button tag={Link} id="cancel-save" to="/related" replace color="info">
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
  referenceIdentifiers: storeState.referenceIdentifier.entities,
  claims: storeState.claim.entities,
  relatedEntity: storeState.related.entity,
  loading: storeState.related.loading,
  updating: storeState.related.updating,
  updateSuccess: storeState.related.updateSuccess,
});

const mapDispatchToProps = {
  getReferenceIdentifiers,
  getClaims,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RelatedUpdate);
